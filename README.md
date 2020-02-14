# Buffet-Reservation-System

This repository contains source code of a buffet reservation system, that accepts advanced bookings. The requesting customer needs to provide his/her name, phone number, total seats needed (total persons to come), and the target dining day to come.

The restaurant has ten tables for 2 persons (Code T01, T02, ..T10), six tables for 4 persons (Code F01, F02, .. F06), and three tables for 8 persons (Code H01, H02, H03). Upon a booking request is reorded, the system will put the record in a pending status. The staff-in-charge can check for available tables for the target day, and then assign the table(s) for the booking request. The status of the booking will then be changed into an accepted state.

The system also accepts cancellation of booking, regardless of whether it is a pending or an accepted booking.
The system can generate listing for both pending and accepted bookings. For pending bookings, "pending" is shown in the listing. For accepted bookings, the codes of the assigned tables are shown.

The system also provides a handy tool, that suggests which table(s) are to be assigned for a booking, based on the number of seats needed by the booking, and the available tables.

To facilitate checking and identification, the system has a ticketing system. For each business day, one ticketing sequence (start from No. 1, then No. 2, and so on) is maintained. For example, if Mr White is the first one to place a booking for Mar 22, then he gets ticket code No. 1. The next customer to place a booking for Mar 22 will get ticket code No. 2. To make sure the identification is unique, the ticket code for each business day is not reused. For example, even if one cancels his/her booking, the ticket code that was already given to that booking will not be reused. 

In addition, the system allows undo/redo and handles most error cases using Exception handling. 

