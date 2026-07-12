#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    struct NearestTarget{
        int sourceRow;
        int sourceCol;
        int distance;
        int targetRow;
        int targetCol;

        NearestTarget(int sourceRow,int sourceCol,int distance,int targetRow,int targetCol) {
            this->sourceRow = sourceRow;
            this->sourceCol = sourceCol;
            this->distance = distance;
            this->targetRow = targetRow;
            this->targetCol = targetCol;
        }
    };

    vector<NearestTarget> nearestTargets(vector<vector<char>> &graph) {
        vector<NearestTarget> answer;
        vector<pair<int,int>> targets;
        for (int i = 0; i<graph.size(); ++i) {
            for (int j = 0; j<graph[i].size(); ++j) {
                if (graph[i][j] == 'T') {
                    targets.push_back({i,j});
                }
            }
        }
        queue<NearestTarget> bfsQueue;
        // Initialize with false
        vector<vector<bool>> visited(graph.size(), vector<bool>(graph[0].size(), false));
        vector<pair<int, int>> moves = {{1,0},{-1,0},{0,1},{0,-1}};
        for (pair<int,int> target : targets) {
            bfsQueue.push(NearestTarget(target.first, target.second, 0, target.first, target.second));
            visited[target.first][target.second] = true;
        }
        while (!bfsQueue.empty()) {
            NearestTarget temp = bfsQueue.front();
            bfsQueue.pop();
            if (graph[temp.sourceRow][temp.sourceCol] == 'S') {
                //add to answer
                answer.push_back(temp);
            }
            for (pair<int,int> move : moves) {
                int newRow = temp.sourceRow + move.first;
                int newCol = temp.sourceCol + move.second;
                if (newRow>=0 && newRow<graph.size() && newCol>=0 && newCol<graph[0].size()){
                    if (!visited[newRow][newCol] && graph[newRow][newCol] != '#') {
                        bfsQueue.push(NearestTarget(newRow, newCol, temp.distance+1, temp.targetRow, temp.targetCol));
                        visited[newRow][newCol] = true;
                    }
                }
            }
        }
        return answer;
    }
};