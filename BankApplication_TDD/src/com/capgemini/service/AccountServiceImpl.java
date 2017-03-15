package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitalAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

	AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber, int amount) throws InsufficientInitalAmountException {
		if (amount < 500) {
			throw new InsufficientInitalAmountException();
		}

		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);

		if (accountRepository.save(account)) {
			return account;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.AccountService#depositAmount(int, int)
	 */
	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException

	{
		Account account = accountRepository.searchAccount(accountNumber);

		if (account == null) {
			throw new InvalidAccountNumberException();

		} else {

			account.setAmount(account.getAmount() + amount);
			return account.getAmount();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.AccountService#withdrawAmount(int, int)
	 */
	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException, InsufficientBalanceException {
		Account account = accountRepository.searchAccount(accountNumber);
		if (account == null) {
			throw new InvalidAccountNumberException();

		} else if (account.getAmount() < amount) {
			throw new InsufficientBalanceException();

		} else {

			account.setAmount(account.getAmount() - amount);

			return account.getAmount();

		}

	}

}
