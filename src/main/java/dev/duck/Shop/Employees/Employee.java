package dev.duck.Shop.Employees;

public record Employee(
    Integer id,
    String firstName,
    String surName,
    String employeePos,
    Boolean employeeGender,
    Integer age,
    Integer maxSales
) {

}
