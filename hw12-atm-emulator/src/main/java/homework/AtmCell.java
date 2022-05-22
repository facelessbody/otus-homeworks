package homework;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.stream.Stream;

import homework.exceptions.AtmIllegalBanknoteNominalException;

public class AtmCell {
    private final BanknoteNominal nominal;
    private final Queue<Banknote> stored = new ArrayDeque<>();

    public AtmCell(BanknoteNominal nominal) {
        this.nominal = nominal;
    }

    public void insert(Collection<Banknote> banknotes) {
        var allMatched = banknotes.stream().allMatch(x -> x.nominal() == nominal);
        if (!allMatched) {
            throw new AtmIllegalBanknoteNominalException();
        }
        stored.addAll(banknotes);
    }

    public Collection<Banknote> take(int count) {
        return Stream.generate(stored::remove).limit(count).toList();
    }

    public int getAmount() {
        return stored.size();
    }

    public long countTotal() {
        return stored.size() * nominal.value();
    }
}
