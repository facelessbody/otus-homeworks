package homework;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import homework.exceptions.AtmFailedToCollectRequestedAmountException;

import static java.util.function.Predicate.not;

public class SomeSplitOnAddendsMethod implements AtmSplitOnAddendsMethod {
    @Override
    public Map<BanknoteNominal, Integer> solve(long requestedAmount, Map<BanknoteNominal, Integer> availableNominals)
            throws AtmFailedToCollectRequestedAmountException {
        var result = new HashMap<BanknoteNominal, Integer>();

        var left = requestedAmount;
        do {
            var maxBanknoteNominal = availableNominals.keySet().stream()
                    .filter(v -> v.value() <= requestedAmount)
                    .filter(not(result::containsKey))
                    .max(Comparator.comparing(BanknoteNominal::value))
                    .orElseThrow(AtmFailedToCollectRequestedAmountException::new);
            var n = Math.min(
                    availableNominals.getOrDefault(maxBanknoteNominal, 0),
                    (int) (left / maxBanknoteNominal.value())
            );
            result.put(maxBanknoteNominal, n);

            left -= n * maxBanknoteNominal.value();
        } while (left > 0);
        return result;
    }
}
