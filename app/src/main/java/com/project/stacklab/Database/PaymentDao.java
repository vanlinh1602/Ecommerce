package com.project.stacklab.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.stacklab.Models.PaymentModel;
import com.project.stacklab.Models.WishlistModel;

import java.util.List;

@Dao
public interface PaymentDao {
    @Insert
    void insertPayment(PaymentModel info);

    @Query("DELETE FROM payment WHERE id = :id")
    void deletePayment(int id);

    @Query("SELECT * FROM payment")
    List<PaymentModel> getPayment();

    @Query("SELECT * FROM payment WHERE id = :id")
    PaymentModel getPaymentById(int id);

    @Query("UPDATE payment SET name = :name, email = :email, phone = :phone, address = :address, city = :city, zipcode = :zipcode WHERE id = :id")
    void updatePayment(long id, String name, String email, String phone, String address, String city, String zipcode);

}
