package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Address;
import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.entities.Inventory;
import com.erhan.dvdrental.entities.Payment;
import com.erhan.dvdrental.entities.Rental;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.CustomerFacade;
import com.erhan.dvdrental.jpa.facade.InventoryFacade;
import com.erhan.dvdrental.jpa.facade.PaymentFacade;
import com.erhan.dvdrental.jpa.facade.RentalFacade;
import com.erhan.dvdrental.utils.BeanBasedUtils;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

@Named("rentFilmBacking")
@ViewScoped
public class RentFilmBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private RentalFacade rentalFacade;

    @EJB
    private InventoryFacade inventoryFacade;

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    PaymentFacade paymentFacade;

    private String inventoryId;
    private Inventory inventory;
    private List<BillItem> billItemList;
    private BigDecimal totalPrice;
    private String customerStatus;
    private Customer customer = null;
    private Rental rental;
    private Date calculatedReturnDate;

    private CreateCustomerBacking createCustomerBacking;

    @PostConstruct
    public void init() {
        createCustomerBacking = BeanBasedUtils.findBeanFromCurrentContext("createCustomerBacking");
        billItemList = new LinkedList<>();
        totalPrice = BigDecimal.ZERO;
    }

    public RentFilmBacking() {
    }

    public RentalFacade getRentalFacade() {
        return rentalFacade;
    }

    public void setRentalFacade(RentalFacade rentalFacade) {
        this.rentalFacade = rentalFacade;
    }

    public InventoryFacade getInventoryFacade() {
        return inventoryFacade;
    }

    public void setInventoryFacade(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public PaymentFacade getPaymentFacade() {
        return paymentFacade;
    }

    public void setPaymentFacade(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<BillItem> getBillItemList() {
        return billItemList;
    }

    public void setBillItemList(List<BillItem> billItemList) {
        this.billItemList = billItemList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Date getCalculatedReturnDate() {
        return calculatedReturnDate;
    }

    public void setCalculatedReturnDate(Date calculatedReturnDate) {
        this.calculatedReturnDate = calculatedReturnDate;
    }

    public void hideRentFilmDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public void findInventory() {
        System.out.println("findInventory method is invoked.");
        Inventory foundInventory = inventoryFacade.find(Integer.valueOf(inventoryId));
        if (foundInventory != null) {
            if (isInventoryInStock(foundInventory)) {
                setInventory(foundInventory);
                setInventoryId(foundInventory.getInventoryId() + " - " + foundInventory.getFilm().getTitle());
            } else {
                setInventory(null);
                setInventoryId("Film stokta yok");
            }
        } else {
            setInventory(null);
            setInventoryId("Bu envanter numarası mevcut değil.");
        }
    }
    
    public boolean isInventoryInStock(Inventory inventoryForCheck) {
        List<Rental> rentalListForInventory = rentalFacade.findByInventory(inventoryForCheck);
        boolean isInStock = true;
        if (rentalListForInventory == null) {
            isInStock = true;
        } else {
            for (Rental rentalForCheck : rentalListForInventory) {
                if (rentalForCheck.getReturnDate() == null) {
                    isInStock = false;
                }
            }
        }
        return isInStock;
    }

    public List<Customer> completeCustomer(String query) {
        System.out.println("completeCustomer method is invoked.");
        query = query.trim();
        query = query.toLowerCase(Locale.ENGLISH);
        List<Customer> filteredCustomers = customerFacade.findByFirstNameContains(query);
        return filteredCustomers;
    }

    public String onFlow(FlowEvent flowEvent) {
        System.out.println("onFlow method is invoked.");
        billItemList.clear();
        billItemList.add(new BillItem(
                        BillItem.ItemType.RENTAL,
                        inventory.getInventoryId(),
                        inventory.getFilm().getTitle(),
                        1,
                        inventory.getFilm().getRentalRate()
                ));
        if (customerStatus.equals("new")) {
            customer = new Customer();
            customer.setFirstName(createCustomerBacking.getFirstName());
            customer.setLastName(createCustomerBacking.getLastName());
            customer.setEmail(createCustomerBacking.getEmail());
            Address customerAddress = new Address();
            customerAddress.setAddress(createCustomerBacking.getAddress1());
            customerAddress.setAddress2(createCustomerBacking.getAddress2());
            customerAddress.setCity(createCustomerBacking.getCity());
            customerAddress.setDistrict(createCustomerBacking.getDistrict());
            customerAddress.setPostalCode(createCustomerBacking.getPostalCode());
            customerAddress.setPhone(createCustomerBacking.getPhone());
            customer.setAddress(customerAddress);
        } else {
            List<Rental> overdueRentalsForCustomer = findOverdueRentalsForCustomer();
            populateBillItemList(overdueRentalsForCustomer, billItemList);
        }

        BigDecimal totalBillPrice = BigDecimal.ZERO;
        if (billItemList != null && billItemList.size() > 0) {
            for (BillItem billItem : billItemList) {
                totalBillPrice = totalBillPrice.add(billItem.getTotalPrice());
            }
        }
        setTotalPrice(totalBillPrice);

        return flowEvent.getNewStep();
    }

    public List<Rental> findOverdueRentalsForCustomer() {
        List<Rental> rentalListreturnDateIsNullByCustomer = rentalFacade.findReturnDateIsNullByCustomer(customer);
        List<Rental> overdueRentalList = new ArrayList<>();
        if (rentalListreturnDateIsNullByCustomer != null && rentalListreturnDateIsNullByCustomer.size() > 0) {
            for (Rental rentalForCustomer : rentalListreturnDateIsNullByCustomer) {
                short rentalDuration = rentalForCustomer.getInventory().getFilm().getRentalDuration();
                long diff = new Date().getTime() - rentalForCustomer.getRentalDate().getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if (days > rentalDuration) {
                    overdueRentalList.add(rentalForCustomer);
                }
            }
        }
        return overdueRentalList;
    }

    public void populateBillItemList(List<Rental> overdueRentalList, List<BillItem> billItemListToPopulate) {
        if (overdueRentalList != null && overdueRentalList.size() > 0) {
            for (Rental overdueRental : overdueRentalList) {
                long days = TimeUnit.DAYS.convert((new Date().getTime() - overdueRental.getRentalDate().getTime()), TimeUnit.MILLISECONDS);
                if (days < overdueRental.getInventory().getFilm().getRentalDuration() * 2) {
                    billItemListToPopulate.add(new BillItem(
                            BillItem.ItemType.OVERDUE,
                            overdueRental.getInventory().getInventoryId(),
                            overdueRental.getInventory().getFilm().getTitle(),
                            Integer.valueOf(String.valueOf(days)),
                            new BigDecimal(1)
                    ));
                } else {
                    billItemListToPopulate.add(new BillItem(
                            BillItem.ItemType.OVERDUE,
                            overdueRental.getInventory().getInventoryId(),
                            overdueRental.getInventory().getFilm().getTitle(),
                            Integer.valueOf(String.valueOf(days)),
                            new BigDecimal(1)
                    ));
                    billItemListToPopulate.add(new BillItem(
                            BillItem.ItemType.REPLACEMENT,
                            overdueRental.getInventory().getInventoryId(),
                            overdueRental.getInventory().getFilm().getTitle(),
                            1,
                            overdueRental.getInventory().getFilm().getReplacementCost()
                    ));
                }
            }
        }
    }

    public void rentFilm() {
        System.out.println("rentFilm method is invoked.");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Staff currentStaff = (Staff) sessionMap.get("Staff");

        if (customerStatus.equals("new")) {
            createCustomerBacking.createCustomer();
            setCustomer(customerFacade.find(createCustomerBacking.getCustomerId()));
            System.out.println("Customer is created.");
        }

        rental = new Rental();
        rental.setRentalDate(new Date());
        rental.setLastUpdate(new Date());
        rental.setInventory(inventory);
        rental.setCustomer(customer);
        rental.setStaff(currentStaff);
        rentalFacade.create(rental);
        System.out.println("Rental is created.");

        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setLastUpdate(new Date());
        payment.setCustomer(customer);
        payment.setStaff(currentStaff);
        payment.setRental(rental);
        payment.setAmount(totalPrice);
        paymentFacade.create(payment);
        System.out.println("Paymet is created.");

        rental.getPaymentList().add(payment);
        rentalFacade.edit(rental);
        System.out.println("Rental is updated.");

        customer.getRentalList().add(rental);
        customer.getPaymentList().add(payment);
        customerFacade.edit(customer);
        System.out.println("Customer is updated.");

        GregorianCalendar calculatedReturnGC = new GregorianCalendar();
        calculatedReturnGC.setTime(rental.getRentalDate());
        calculatedReturnGC.add(GregorianCalendar.DAY_OF_MONTH, inventory.getFilm().getRentalDuration());
        setCalculatedReturnDate(calculatedReturnGC.getTime());

    }

}
