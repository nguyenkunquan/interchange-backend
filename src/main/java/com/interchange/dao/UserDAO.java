//package com.interchange.dao;
//
//import com.interchange.entities.User;
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class UserDAO {
//    @Autowired
//    private EntityManager entityManager;
//    public User checkLoginByUserIdOrPhoneNumberOrEmail(String username) {
//        try {
//            String sql = "SELECT u FROM User u WHERE u.userId = :username " +
//                    "OR u.phoneNumber = :username OR u.email = :username";
//            Query query = entityManager.createQuery(sql,User.class);
//            query.setParameter("username", username);
//            return (User) query.getSingleResult();
//        } catch (NoResultException ex) {
//            System.out.println("The user with ID: " + username + " can't found");
//            return null;
//        }
//    }
//}
