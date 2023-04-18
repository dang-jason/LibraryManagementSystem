package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Item implements Serializable{
    protected String itemType;
    protected String name;
    protected String creator;
    protected int pages_year;
    protected String summary;
    protected String current;
    protected String previous;
    protected String returnDate;
    protected String checkoutDate;

    public Item(){
        this.itemType = "";
        this.name = "";
        this.creator = "";
        this.pages_year = 0;
        this.summary = "";
        this.current = "";
        this.previous ="";
        this.returnDate = "";
        this.checkoutDate = "";
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Item(String itemType, String name, String creator, int pages_year, String summary, String current, String previous, String returnDate, String checkoutDate){
        this.itemType = itemType;
        this.name = name;
        this.creator = creator;
        this.pages_year = pages_year;
        this.summary = summary;
        this.current = current;
        this.previous = previous;
        this.returnDate = returnDate;
        this.checkoutDate = checkoutDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemType='" + itemType + '\'' +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", pages_year=" + pages_year +
                ", summary='" + summary + '\'' +
                ", current='" + current + '\'' +
                ", previous='" + previous + '\'' +
                '}';
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getPages_year() {
        return pages_year;
    }

    public void setPages_year(int pages_year) {
        this.pages_year = pages_year;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
