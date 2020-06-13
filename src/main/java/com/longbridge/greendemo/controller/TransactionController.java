//package com.longbridge.greendemo.controller;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.longbridge.greendemo.Application;
//import com.longbridge.greendemo.exception.ResourceNotFoundException;
//import com.longbridge.greendemo.model.Transaction;
//import com.longbridge.greendemo.model.User;
//import com.longbridge.greendemo.repository.TransactionRepository;
//import com.longbridge.greendemo.repository.UserRepository;
//
///**
// * The type Transaction controller.
// *
// * @author idisimagha dublin-green
// */
//@RestController
//@RequestMapping("/api/v1/transaction")
//@CrossOrigin(origins = "http://localhost:8080")
//public class TransactionController {
//
//	static Logger log = Logger.getLogger(Application.class.getName());
//
//	@Autowired
//	private TransactionRepository transactionRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	/**
//	 * Get all users list.
//	 *
//	 * @return the list
//	 */
//	@GetMapping("/getAllTransactions")
//	public List<Transaction> getAllTransactions() {
//		log.info("getAllTransactions : " + transactionRepository.findAll() + "----------" + new Date());
//		return transactionRepository.findAll();
//	}
//
//	/**
//	 * Create transaction.
//	 *
//	 * @param user the user
//	 * @return User
//	 * @throws ResourceNotFoundException the resource not found exception
//	 */
//	@PostMapping("/createUser/{id}")
//	public Transaction createUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody Transaction transaction)
//			throws ResourceNotFoundException {
//		User user = userRepository.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
//		long returnUserId = user.getId();
//		log.info("createUser : " + transaction + "----------------" + returnUserId + new Date());
//		return transactionRepository.save(transaction);
//	}
//
//}
