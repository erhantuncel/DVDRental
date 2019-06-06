package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Payment;
import com.erhan.dvdrental.entities.Rental;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.PaymentFacade;
import com.erhan.dvdrental.jpa.facade.RentalFacade;
import com.erhan.dvdrental.utils.BeanBasedUtils;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("returnFilmBacking")
@ViewScoped
public class ReturnFilmBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private RentalFacade rentalFacade;

    @EJB
    private PaymentFacade paymentFacade;
    
    private String rentalId;
    private Rental rental;
    private Integer rentDuration;
    private List<BillItem> billItems;
    private BigDecimal totalPrice;
    private boolean retunRentalSuccessfull = false;

    private RentFilmBacking rentFilmBacking;
    
    @PostConstruct
    public void init() {
        rentFilmBacking = BeanBasedUtils.findBeanFromCurrentContext("rentFilmBacking");
        billItems = new LinkedList<>();
        totalPrice = BigDecimal.ZERO;
    }
    
    public ReturnFilmBacking() {
    }

    public RentalFacade getRentalFacade() {
        return rentalFacade;
    }

    public void setRentalFacade(RentalFacade rentalFacade) {
        this.rentalFacade = rentalFacade;
    }

    public PaymentFacade getPaymentFacade() {
        return paymentFacade;
    }

    public void setPaymentFacade(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Integer getRentDuration() {
        return rentDuration;
    }

    public void setRentDuration(Integer rentDuration) {
        this.rentDuration = rentDuration;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isRetunRentalSuccessfull() {
        return retunRentalSuccessfull;
    }

    public void setRetunRentalSuccessfull(boolean retunRentalSuccessfull) {
        this.retunRentalSuccessfull = retunRentalSuccessfull;
    }

    public void hideReturnFilmDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
    
    public void searchRentalById() {
        billItems.clear();
        totalPrice = BigDecimal.ZERO;
        List<Rental> foundRentalList = rentalFacade.findReturnDateIsNullById(Integer.valueOf(rentalId));
        if(foundRentalList != null && foundRentalList.size() > 0) {
            setRental(foundRentalList.get(0));
            long diff = new Date().getTime() - rental.getRentalDate().getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            setRentDuration(Long.valueOf(days).intValue());
            List<Rental> overdueRentalsForCustomer = rentFilmBacking.findOverdueRentalsForCustomer(rental.getCustomer());
            rentFilmBacking.populateBillItemList(overdueRentalsForCustomer, billItems);
            BigDecimal totalBillPrice = rentFilmBacking.calcuateTotalPrice(billItems);
            setTotalPrice(totalBillPrice);
        } else {
            setRental(null);
            setRentDuration(null);
        }
    }

    public String returnFilm() {
        System.out.println("returnFilm method is invoked.");
        Date now = new Date();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Staff currentStaff = (Staff) sessionMap.get("Staff");
        
        rental.setReturnDate(now);
        if(!totalPrice.equals(BigDecimal.ZERO)) {
            Payment payment = new Payment();
            payment.setPaymentDate(now);
            payment.setLastUpdate(now);
            payment.setAmount(totalPrice);
            payment.setCustomer(rental.getCustomer());
            payment.setRental(rental);
            payment.setStaff(currentStaff);
            paymentFacade.create(payment);
            System.out.println("Paymet is created.");
            rental.getPaymentList().add(payment);
        }
        rentalFacade.edit(rental);
        System.out.println("Rental is updated.");
        setRetunRentalSuccessfull(true);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage("actionResult", new FacesMessage(FacesMessage.SEVERITY_INFO, "İşlem başarılı.", null));
        return "index.xhtml?faces-redirect=true";
    }

}
