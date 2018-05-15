#ifndef TRANSACTION_H
#define TRANSACTION_H

#include <iostream>
#include "rental.h"
#include "renter.h"
#include <vector>
class Transaction{

  private:
    float total_price;
    Renter* Customer;
    vector<Rental> rentals;
    int tnew_points;
  public:
    Transaction(Renter* incust)
      :Customer(incust)
      {}
    void print_statement();
    void calc_total();
    void add_points();
    void add_movie(Rental new_rent);
};

#endif
