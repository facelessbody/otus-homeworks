package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> storage = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return ImmutableKeyMapEntry.ofNullable(storage.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return ImmutableKeyMapEntry.ofNullable(storage.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        storage.put(customer, data);
    }

    private record ImmutableKeyMapEntry(
            Map.Entry<Customer, String> delegate
    ) implements Map.Entry<Customer, String> {

        @Override
        public Customer getKey() {
            return new Customer(delegate.getKey());
        }

        @Override
        public String getValue() {
            return delegate.getValue();
        }

        @Override
        public String setValue(String value) {
            return delegate.setValue(value);
        }

        public static ImmutableKeyMapEntry ofNullable(Map.Entry<Customer, String> entry) {
            return Optional.ofNullable(entry).map(ImmutableKeyMapEntry::new).orElse(null);
        }
    }
}
