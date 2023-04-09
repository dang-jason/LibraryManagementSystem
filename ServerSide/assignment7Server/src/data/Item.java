package data;

import java.io.Serializable;
public class Item implements Serializable{
    protected String itemType;
    protected String title;
    protected String author;
    protected int pages;
    protected String summary;

    public Item(){
        this.itemType = "";
        this.title = "";
        this.author = "";
        this.pages = 0;
        this.summary = "";
    }
    public Item(String itemType, String title, String author, int pages, String summary){
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.summary = summary;
        System.out.println("Item values created");
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemType='" + itemType + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", summary='" + summary + '\'' +
                '}';
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
