package com.ansa;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "record", keyspace = "cycling")
public class Record implements Serializable{

    @Column(name = "record_id")
    private String id;
    private String ticker;
    private String side;
    private Long amount;

    public Record(){

    }

    public Record(String ticker, String side, Long amount) {
        this.ticker = ticker;
        this.side = side;
        this.amount = amount;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Record{" +
                "ticker='" + ticker + '\'' +
                ", side='" + side + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (id != null ? !id.equals(record.id) : record.id != null) return false;
        if (ticker != null ? !ticker.equals(record.ticker) : record.ticker != null) return false;
        if (side != null ? !side.equals(record.side) : record.side != null) return false;
        return amount != null ? amount.equals(record.amount) : record.amount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ticker != null ? ticker.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
