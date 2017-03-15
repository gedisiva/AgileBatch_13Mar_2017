package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitalAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import static org.junit.Assert.assertEquals;
public class AccountServiceImplTest {

	@Mock
	AccountRepository accountRepository;
	AccountService accountService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
		
	}
	
	/*
	 * create account
	 * 1.when the amount is less than 500 system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitalAmountException.class)
	public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitalAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitalAmountException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account, accountService.createAccount(101, 5000));
	}

	//deposit.
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNumberIsInvalidSystemShouldThrowException() throws InvalidAccountNumberException
	{
		accountService.depositAmount(1002, 5000);
	}
	
	@Test
	public void whenDepositeIsSuccessfull() throws InvalidAccountNumberException{
		Account account = new Account();
		
		account.setAccountNumber(10002);
		account.setAmount(5000);
		when(accountRepository.searchAccount(10002)).thenReturn(account);
		
		assertEquals(10000,accountService.depositAmount(10002, 5000));
		
		
		
	}
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenInSufficientBalanceForWithdraw() throws InsufficientBalanceException, InvalidAccountNumberException{
		Account account=new Account();
		account.setAccountNumber(10004);
		account.setAmount(5000);
		when(accountRepository.searchAccount(10004)).thenReturn(account);
		
		accountService.withdrawAmount(10004, 10000);
		
	}
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenInValidAccountNumberSystemShouldThrowException() throws InsufficientBalanceException,InvalidAccountNumberException{
		/*Account account=new Account();
		account.setAccountNumber(10004);
		account.setAmount(5000);
		when(accountRepository.searchAccount(10004)).thenReturn(account);*/
		
		accountService.withdrawAmount(10003, 10000);
		
	}
	@Test
	public void whenWithDrawSuccessfull() throws InvalidAccountNumberException, InsufficientBalanceException{
		Account account=new Account();
		account.setAccountNumber(10004);
		account.setAmount(5000);
		when(accountRepository.searchAccount(10004)).thenReturn(account);
		
		assertEquals(account.getAmount()-2000, accountService.withdrawAmount(10004, 2000));
		
		
	}

}
