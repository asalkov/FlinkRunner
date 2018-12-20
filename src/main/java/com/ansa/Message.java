package com.ansa;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;

@Table(keyspace = "test", name = "message")
public class Message implements Serializable{
    private static final long serialVersionUID = 1123119384361005680L;

    @Column(name = "body")
    private String message;

    @Column(name = "value")
    private String value;

    public Message(){

    }

    public Message(String word, String value) {
        this.message = word;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String word) {
        this.message = word;
    }

    public boolean equals(Object other) {
        if (other instanceof Message) {
            Message that = (Message) other;
            return this.message.equals(that.message);
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
