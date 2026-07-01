# Design Uber

Uber is a platform that connects passengers with drivers who offer their personal vehicles in transportation services so that user can book the vehicle near them from their picup point to the specified destination.


### Requirements
Functional Requirements (Features)

- User can request to book a transportation service
- User will be shown an estimated fare by inputting source and destination location
- User should be able to request a ride with a Nearby Driver(s) by selecting an estimated fare
- Drivers should be able to deny / accept a ride request.
- Driver should be able to navigate to pickup and drop location.

Out of Scope

- User can select a mode / type of transportation
- Rating of Drivers and Passengers / Users
- Scheduling Rides

Non-Functional Requirements (Qualities of System - Challenge, System Context, Quantization)

- strong consistency, matching, driver to passenger / user 1:1
- highly available, other than matching
- reads > writes, estimated fare, 100:30
- low latency, ride searching, < 1 min
- high throughput, occasional during events, 10^5+ Requests

Out of Scope

- GDPR compliance & Privacy
- Alerts and Monitoring
- CI / CD / Deployment
- System Failures & Resilience

I prefer Forgoing the estimations at this moment. Instead, I prefer doing it at the time of Design, if that estimate has a direct influence over the design.