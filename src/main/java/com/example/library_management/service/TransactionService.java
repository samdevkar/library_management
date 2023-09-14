package com.example.library_management.service;

import com.example.library_management.dto.InitiateTansactionRequest;
import com.example.library_management.model.*;
import com.example.library_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    StudentService studentService;
    @Autowired
    AdminService adminService;
    @Autowired
    AuthorService authorService;
    @Autowired
    BookService bookService;
    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.allowed.max-books}")
    Integer maxBooksAllowed;

    @Value("${student.allowed.duration}")
    Integer allowedDuration;

    public String initiateTransaction(InitiateTansactionRequest initiateTansactionRequest) throws Exception{
        return initiateTansactionRequest.getTransactionType()== TransactionType.RETURN
                ? returnBook(initiateTansactionRequest)
                : issueBook(initiateTansactionRequest);

    }
    private String issueBook(InitiateTansactionRequest initiateTansactionRequest) throws Exception{
        List<Student> studentList=studentService.findStudent("rollNumber",initiateTansactionRequest.getRollNumber());
        Student student=studentList.size()>0 ? studentList.get(0):null;

        List<Book>bookList=bookService.findBook("id",String.valueOf(initiateTansactionRequest.getBookId()));
        Book book =bookList.size()>0 ?bookList.get(0):null;

        Admin admin=adminService.find(initiateTansactionRequest.getAdminId());
        if(student ==null || book ==null || admin==null){
            throw new Exception("Invalid Request");
        }
        if(book.getStudent() !=null){
            throw new Exception("Book is already issued"+book.getStudent().getName());
        }

        if(student.getBookList().size()>=maxBooksAllowed){
            throw new Exception("Issue limit is reached to this Student");

        }

        Transaction transaction=null;
        try{
            transaction=Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionStatus(TransactionStatus.PENDING)
                    .transactionType(TransactionType.ISSUE)
                    .build();

            transactionRepository.save(transaction);
            book.setStudent(student);
            bookService.createOrUpdateBook(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }
        finally {
            transactionRepository.save(transaction);
        }
       return transaction.getTransactionId();
    }


    private String returnBook(InitiateTansactionRequest initiateTansactionRequest) throws Exception{
        List<Student> studentList=studentService.findStudent("rollNumber",initiateTansactionRequest.getRollNumber());
        Student student=studentList.size()>0 ? studentList.get(0):null;

        List<Book>bookList=bookService.findBook("id",String.valueOf(initiateTansactionRequest.getBookId()));
        Book book =bookList.size()>0 ?bookList.get(0):null;

        Admin admin=adminService.find(initiateTansactionRequest.getAdminId());
        if(student ==null || book ==null || admin==null){
            throw new Exception("Invalid Request");
        }
        if(book.getStudent()==null || !book.getStudent().getId().equals(student.getId())){
            throw new Exception("This book is not issued to this student");
        }
        Transaction issuanceTransaction=transactionRepository.findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(student,book,TransactionType.ISSUE);
        if(issuanceTransaction==null){
            throw new Exception("This book is not issued to anyone");
        }
        Transaction transaction=null;
        try {
            Integer fine = calculateFine(issuanceTransaction.getCreatedOn());
            transaction = Transaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .transactionType(initiateTansactionRequest.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .fine(fine)
                    .build();
            transactionRepository.save(transaction);
            if (fine == 0) {
                book.setStudent(null);
                bookService.createOrUpdateBook(book);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            }
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }
        finally{
            transactionRepository.save(transaction);
        }
        return transaction.getTransactionId();


    }
    private Integer calculateFine(Date issueTime){
        long issuaneTime=issueTime.getTime();
        long currentTime=System.currentTimeMillis();
        long dif=currentTime-issuaneTime;
        long days= TimeUnit.DAYS.convert(dif,TimeUnit.MILLISECONDS);
        if(days>allowedDuration){
            return (int)(days-allowedDuration);
        }
        return  0;

    }
}
