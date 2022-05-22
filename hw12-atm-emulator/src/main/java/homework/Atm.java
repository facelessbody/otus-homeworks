package homework;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import homework.exceptions.AtmIllegalBanknoteNominalException;

public class Atm {
    private final AtmSplitOnAddendsMethod splitOnAddendsMethod;
    private final Map<BanknoteNominal, AtmCell> cells;

    Atm(AtmSplitOnAddendsMethod splitOnAddendsMethod, Set<BanknoteNominal> nominals) {
        this.splitOnAddendsMethod = splitOnAddendsMethod;
        this.cells = nominals.stream()
                .collect(Collectors.toMap(Function.identity(), AtmCell::new));
    }

    public void insert(Collection<Banknote> banknotes) {
        var illegalBanknote = banknotes.stream()
                .filter(b -> !cells.containsKey(b.nominal()))
                .findAny();
        if (illegalBanknote.isPresent()) {
            throw new AtmIllegalBanknoteNominalException();
        }
        banknotes.stream()
                .collect(Collectors.groupingBy(Banknote::nominal))
                .forEach((nominal, list) -> cells.get(nominal).insert(list));
    }

    public Collection<Banknote> withdraw(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount should be greater than 0");
        }
        var countedBanknotes = cells.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getAmount()));
        var returningBanknotes = splitOnAddendsMethod.solve(amount, countedBanknotes);

        return mapToStream(returningBanknotes, (nominal, n) -> cells.get(nominal).take(n))
                .flatMap(Collection::stream)
                .toList();
    }

    public long countTotal() {
        return cells.values().stream().map(AtmCell::countTotal).reduce(0L, Long::sum);
    }

    private static <K, V, R> Stream<R> mapToStream(Map<K, V> map, BiFunction<K, V, R> transform) {
        return map.entrySet().stream().map(e -> transform.apply(e.getKey(), e.getValue()));
    }
}
