#include "movie.h"
#include "rental.h"
#include "renter.h"
#include "transaction.h"
#include <iomanip>

void Transaction::add_movie(Rental new_rent){
  rentals.push_back(new_rent);

}


void Transaction::add_points(){
  vector<Rental>::iterator i;
  tnew_points = 0;
  for (i = rentals.begin(); i != rentals.end(); ++i)
  {
      Rental& rental = *i;
      bool is_new = rental.get_release();
      if (is_new == 1){
        tnew_points=tnew_points +2;
      }
      else{
        tnew_points=tnew_points +1;
      }
  }
  Customer->set_points(tnew_points);
}


void Transaction::calc_total(){
  vector<Rental>::iterator i;
  total_price = 0;
  for (i = rentals.begin(); i != rentals.end(); ++i)
  {
      Rental& rental = *i;
      total_price=total_price+ rental.get_price();
  }

}

void Transaction::print_statement(){
  vector<Rental>::iterator i;
  cout << "********************************************" << endl;
  cout << "********  S A L E S  R E C E I P T  ********" << endl;
  cout << "********************************************" << endl;
  cout << "**                                        **" << endl;
  cout << "**                                        **" << endl;
  for (i = rentals.begin(); i != rentals.end(); ++i)
  {
      Rental& rental = *i;
      cout << "**   Movie:" << setw(12) <<left<< rental.get_movie_name()
      <<  right <<setw(10) << "$" << setw(5)<< fixed
       <<setprecision(2) << rental.get_price() << "    **" << endl;
      cout << "**            $" <<setw(3)<< fixed
       <<setprecision(2) <<rental.get_daily_price()
        << "/day  for ";
      cout << setprecision(0) << setw(2)<<rental.get_length()<<" day(s)    **" << endl;
      int points = 1;
      if (rental.get_release() ==1){
        points = 2;
      }
      cout << "**"<< setw(25)<< "Type:" <<setw(11) << rental.get_movie_type() <<"    **"
        <<endl<<"**"<<setw(31) << "  You get "<< points<<" pts    **" << endl;
  }
  cout << "**                                        **" << endl;
  cout << "**                                        **" << endl;
  cout << "********************************************" << endl;
  cout << "**  Total Sales:    $" << setw(9)<< fixed <<setprecision(2)
    << total_price << "            **" << endl;
  cout << "**  New Points:      " << setw(9)
    << tnew_points << "            **" << endl;
  cout << "**  Total Points:    " << setw(9)
    << Customer->get_points() << "            **" << endl;
  cout << "********************************************" << endl;
  cout << "**                                        **" << endl;
  cout << "**          Have a Good Day " <<setw(10)<<left
    << Customer->get_name()<<"    **" << endl;
  cout << "**                                        **" << endl;
  cout << "********************************************" << endl;



}
