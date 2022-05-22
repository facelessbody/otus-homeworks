package homework;

import java.util.Map;

import homework.exceptions.AtmFailedToCollectRequestedAmountException;

public interface AtmSplitOnAddendsMethod {
    Map<BanknoteNominal, Integer> solve(long requestedAmount, Map<BanknoteNominal, Integer> availableNominals)
            throws AtmFailedToCollectRequestedAmountException;

}
