package homework;


import java.util.Stack;

public class CustomerReverseOrder {

    private final Stack<Customer> storage = new Stack<>();

    public void add(Customer customer) {
        storage.add(customer);
    }

    public Customer take() {
        return storage.pop();
    }
}
