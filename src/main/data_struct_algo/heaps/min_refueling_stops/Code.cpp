#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    struct Station{
        int fuel;
        int position;

        bool operator()(Station &a, Station &b){
            return a.fuel < b.fuel;
        }
    };

    /*
     * Gives TLE
     */
    int minRefuelStops(int target, int startFuel, vector<vector<int>>& stations) {
        int currFuel = startFuel;
        int stops = 0;
        priority_queue<Station, vector<Station>, Station> stationHeap;
        int stationNo = 0;
        for(int i =0; i<target; i++){
            if (stationNo < stations.size() && stations[stationNo][0] == i) {
                Station s;
                s.fuel = stations[stationNo][1];
                s.position = stations[stationNo][0];
                stationHeap.push(s);
                stationNo++;
            }
            if (currFuel == 0) {
                if (stationHeap.size()==0) {
                    stops = -1;
                    break;
                } else {
                    currFuel += stationHeap.top().fuel;
                    stationHeap.pop();
                    stops++;
                }
            }
            currFuel--;
        }
        return stops;
    }

};