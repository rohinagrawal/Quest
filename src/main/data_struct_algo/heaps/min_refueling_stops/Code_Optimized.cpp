#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    struct Station{
        int fuel;
        int position;
    };

    int minRefuelStops(int target, int startFuel, vector<vector<int>>& stations) {
        int currFuel = startFuel;
        auto comparator = [](const Station &a, const Station &b) {
            return a.fuel < b.fuel;
        };
        priority_queue<Station, vector<Station>, decltype(comparator)> stationHeap(comparator);
        int stops = 0;
        int stationNo = 0;
        while (currFuel<target) {
            while (stationNo < stations.size() && stations[stationNo][0]<=currFuel) {
                Station s;
                s.fuel = stations[stationNo][1];
                s.position = stations[stationNo][0];
                stationHeap.push(s);
                stationNo++;
            }
            if (stationHeap.size()==0) {
                return -1;
            }

            currFuel += stationHeap.top().fuel;
            stationHeap.pop();
            stops++;

        }
        return stops;
    }

    int minRefuelStops_dirtyImplementation(int target, int startFuel, vector<vector<int>>& stations) {
        int currFuel = startFuel;
        priority_queue<Station, vector<Station>, decltype(comparator)> stationHeap(comparator);
        int stops = 0;
        int stationNo = 0;
        while (currFuel < target && stationNo < stations.size()) {
            while (currFuel - stations[stationNo][0] < 0 && stationHeap.size() > 0) {
                currFuel += stationHeap.top().fuel;
                stationHeap.pop();
                stops++;
            }
            int state = currFuel - stations[stationNo][0];
            if (state<0) {
                return -1;
            } else {
                Station s;
                s.fuel = stations[stationNo][1];
                s.position = stations[stationNo][0];
                stationHeap.push(s);
                stationNo++;
            }
        }
        if (currFuel >= target) {
            return stops;
        } else {
            return -1;
        }
    }

};