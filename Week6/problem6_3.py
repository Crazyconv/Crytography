# should set A = 3p+2q, because 3p+2q is odd
import gmpy2

N = 720062263747350425279564435525583738338084451473999841826653057981916355690188337790423408664187663938485175264994017897083524079135686877441155132015188279331812309091996246361896836573643119174094961348524639707885238799396839230364676670221627018353299443241192173812729276147530748597302192751375739387929
A = 2 * gmpy2.isqrt(6 * N) + 1

x = gmpy2.isqrt(A * A - 24 * N)

print (A * A - 24 * N == x * x)

p1 = (A - x) / 6
q1 = (A - 3 * p1) / 2
p2 = (A + x) / 6
q2 = (A - 3 * p2) / 2

if p1 * q1 == N:
    if p1 > q1:
        print q1
    else:
        print p1
else:
    if p2 > q2:
        print q2
    else:
        print p2