#include "movie.h"
#include "rental.h"
#include "renter.h"
#include "transaction.h"
#include <iostream>
#include <string>
using namespace std;
int main(){

  // cout << "Hello!" << endl;
  //
  //
  // Renter new_rent("Bob",5);
  //
  // cout << "Name is: " << new_rent.get_name() << endl;
  // cout << "points are: " << new_rent.get_points() << endl;
  //
  // Movie new_movie("Harry Potter", "test");
  // cout << "Movie is: " << new_movie.get_title() << endl;
  // cout << "Type is: " << new_movie.get_type() << endl;
  //
  // Rental new_rental(&new_movie,5);
  // cout << "movieprice is: " << new_rental.get_price() << endl;
  // cout << "new_release is: " << new_rental.get_release() << endl;
  // cout << "rental_name is: " << new_rental.get_movie_name() << endl;
  // cout << "rental_type is: " << new_rental.get_movie_type() << endl;

  Movie avengers("Avengers", "new release");
  Movie bambi("Bambi", "children");
  Movie up("UP", "regular");
  Movie ironman("Iron Man", "regular");
  Rental av_rental(&avengers,0);
  Rental ba_rental(&bambi,0);
  Rental up_rental(&up,0);
  Rental im_rental(&ironman,0);
  string name;
  int points;
  int input_movies;
  int choice;;
  string type;
  int days;
  cout << "Whats your name?" << endl;
  cin >> name;
  cout << "How many frequent points do you have??" << endl;
  cin >> points;
  Renter new_rent(name,points);
  Transaction new_trans(&new_rent);

  cout << "How many movies do you want to rent?" << endl;
  cin >> input_movies;

  for (int i=0; i <input_movies;i++){
    cout << "What movie title do you want to rent(1/2/3/4)?" << endl;
    cout << "    1.Avengers(new release)"<< endl
      << "    2.Bambi(children)"<< endl
      << "    3.Up(regular)"<< endl
      << "    4.Iron Man(regular)"<< endl;
    cin >> choice;

    cout << "How Long do you want to rent??" << endl;
    cin >> days;

    switch (choice)
   {
       case 1: printf("Choice is Avengers\n");
               av_rental.set_length(days);
               new_trans.add_movie(av_rental);
               break;
       case 2: printf("Choice is Bambi\n");
               ba_rental.set_length(days);
               new_trans.add_movie(ba_rental);
               break;
       case 3: printf("Choice is Up\n");
               up_rental.set_length(days);
               new_trans.add_movie(up_rental);
               break;
       case 4: printf("Choice is Iron Man\n");
               im_rental.set_length(days);
               new_trans.add_movie(im_rental);
               break;
       default: printf("Choice other than 1, 2, 3 and 4\n");
               break;
   }


  }
  new_trans.calc_total();
  new_trans.add_points();
  new_trans.print_statement();

return 0;
}
