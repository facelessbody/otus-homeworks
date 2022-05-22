package homework;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import homework.exceptions.AtmFailedToCollectRequestedAmountException;
import homework.exceptions.AtmIllegalBanknoteNominalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AtmTest {
    private static final BanknoteNominal BN100 = new BanknoteNominal(100);
    private static final BanknoteNominal BN200 = new BanknoteNominal(200);
    private static final BanknoteNominal BN500 = new BanknoteNominal(500);

    private Atm atm;

    @BeforeEach
    public void initAtm() {
        atm = new Atm(new SomeSplitOnAddendsMethod(), Set.of(BN100, BN200, BN500));
    }

    @Test
    public void shouldReturnZeroWhenEmpty() {
        Assertions.assertEquals(0, atm.countTotal());
    }

    @Test
    public void shouldChangeTotalCountAfterInsertion() {
        atm.insert(List.of(
                new Banknote(BN100),
                new Banknote(BN100),
                new Banknote(BN500)
        ));
        Assertions.assertEquals(700, atm.countTotal());
    }

    @Test
    public void shouldThrowWhenInsertBanknoteWithNotAllowedNominal() {
        var illegalBanknoteNominal = new BanknoteNominal(50);
        Assertions.assertThrows(AtmIllegalBanknoteNominalException.class,
                () -> atm.insert(List.of(new Banknote(BN100), new Banknote(illegalBanknoteNominal))));
    }

    @Test
    public void shouldReturnBanknotesWithRequiredAmount() {
        atm.insert(List.of(
                new Banknote(BN100),
                new Banknote(BN100),
                new Banknote(BN100),
                new Banknote(BN500)
        ));
        var banknotes = atm.withdraw(700);
        Assertions.assertIterableEquals(
                List.of(BN100, BN100, BN500),
                banknotes.stream()
                        .map(Banknote::nominal)
                        .sorted(Comparator.comparing(BanknoteNominal::value))
                        .collect(Collectors.toList())
        );
        Assertions.assertEquals(100, atm.countTotal());

    }

    @ParameterizedTest
    @ValueSource(longs = {1, 150, 300, 1000})
    public void shouldThrowWhenCannotCollectRequestedAmount(long requestedAmount) {
        atm.insert(List.of(
                new Banknote(BN100),
                new Banknote(BN100),
                new Banknote(BN500)
        ));
        Assertions.assertThrows(AtmFailedToCollectRequestedAmountException.class,
                () -> atm.withdraw(requestedAmount));
    }
}