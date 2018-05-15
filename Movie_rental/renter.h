#ifndef RENTER_H
#define RENTER_H
#include <string>

class Renter{

  private:
    string name;
    int freq_pts;
  public:
    Renter(string inp_name, int inp_pts)
      :name(inp_name),freq_pts(inp_pts)
      {}
    void set_points(int new_points);
    int get_points();
    string get_name();
};

#endif
