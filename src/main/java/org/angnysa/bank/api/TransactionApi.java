package org.angnysa.bank.api;

import lombok.AllArgsConstructor;
import org.angnysa.bank.api.mapper.RestTransactionMapper;
import org.angnysa.bank.api.model.RestTransaction;
import org.angnysa.bank.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TransactionApi {
    private final TransactionService transactionService;
    private final RestTransactionMapper restTransactionMapper;

    @GetMapping("/api/transactions/{customerId}")
    public ResponseEntity<List<RestTransaction>> getTransactions(@PathVariable("customerId") int customerId) {
        return ResponseEntity.ok(
                transactionService.getTransactions(customerId)
                        .stream()
                        .map(restTransactionMapper::toRest)
                        .collect(Collectors.toList()));
    }

//    @PostMapping("/api/transaction")
//    public ResponseEntity<Void> transferMoney(@RequestParam("toCustomerId") int toCustomerId,
//                                              @RequestParam("amount") long amount) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        int currentCustomerId = Integer.parseInt(authentication.getName());
//        return transferMoney(currentCustomerId, toCustomerId, amount);
//    }

    public ResponseEntity<Void> transferMoney(@RequestParam("fromCustomerId") int fromCustomerId,
                                              @RequestParam("toCustomerId") int toCustomerId,
                                              @RequestParam("amount") long amount) {
        transactionService.transferMoney(fromCustomerId, toCustomerId, amount);
        return ResponseEntity.noContent().build();
    }
}
