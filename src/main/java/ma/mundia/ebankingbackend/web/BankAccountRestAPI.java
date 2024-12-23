package ma.mundia.ebankingbackend.web;

import lombok.AllArgsConstructor;
import ma.mundia.ebankingbackend.dtos.AccountHistoryDTO;
import ma.mundia.ebankingbackend.dtos.AccountOperationDTO;
import ma.mundia.ebankingbackend.dtos.BankAccountDTO;
import ma.mundia.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.mundia.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.mundia.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/{accountId}/debit")
    public void debit(
            @PathVariable String accountId,
            @RequestParam double amount,
            @RequestParam String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.debit(accountId, amount, description);
    }

    @PostMapping("/accounts/{accountId}/credit")
    public void credit(
            @PathVariable String accountId,
            @RequestParam double amount,
            @RequestParam String description) throws BankAccountNotFoundException {
        bankAccountService.credit(accountId, amount, description);
    }

    @PostMapping("/accounts/transfer")
    public void transfer(
            @RequestParam String accountIdSource,
            @RequestParam String accountIdDestination,
            @RequestParam double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.transfer(accountIdSource, accountIdDestination, amount);
    }
}
