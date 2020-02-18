package com.directparking.app.ui.login.model;

import com.directparking.app.ui.signup.model.IdNameItem;

import java.io.Serializable;

public class UserData implements Serializable {

    private String userId;
    private String profile;
    private String firstName;
    private String lastName;
    private int rating;
    private String universityId;
    private IdNameItem university;
    private String email;
    private String dob;
    private String password;
    private int phoneCode;
    private String phone;

    public UserData(String userId, String profile, String firstName, String lastName, int rating, String universityId,
                    IdNameItem university, String email, String dob, String password, int phoneCode, String phone) {
        this.userId = userId;
        this.profile = profile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.universityId = universityId;
        this.university = university;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.phoneCode = phoneCode;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfile() {
        return profile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public String getUniversityId() {
        return universityId;
    }

    public IdNameItem getUniversity() {
        return university;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public int getPhoneCode() {
        return phoneCode;
    }

    public String getPhone() {
        return phone;
    }

    public static class Builder {

        private String userId;
        private String profile;
        private String firstName;
        private String lastName;
        private int rating;
        private String universityId;
        private IdNameItem university;
        private String email;
        private String dob;
        private String password;
        private int phoneCode;
        private String phone;

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setUniversityId(String universityId) {
            this.universityId = universityId;
            return this;
        }

        public Builder setUniversity(IdNameItem university) {
            this.university = university;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setPhoneCode(int phoneCode) {
            this.phoneCode = phoneCode;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserData build() {
            return new UserData(userId, profile, firstName, lastName, rating, universityId, university, email, dob, password, phoneCode, phone);
        }
    }
}
