package com.example.collageapp.ebook;

public class EbookModal {
    private String pdftitle,url,date,time;


    public EbookModal(String pdftitle, String url, String date, String time) {
        this.pdftitle = pdftitle;
        this.url = url;
        this.date = date;
        this.time = time;
    }

    public EbookModal() {
    }

    public String getPdfTitle() {
        return pdftitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdftitle = pdfTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
