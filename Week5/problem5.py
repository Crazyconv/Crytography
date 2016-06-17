import gmpy2

def ex(c, d, N):
    i = 1
    m = 1

    # compute c^d mod N
    while i <= d:
        if i & d != 0:
            m = gmpy2.c_mod(m * c, N) + N
        i = i << 1
        c = gmpy2.c_mod(c * c, N) + N
    return m

p = 13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171

g = 11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568

h = 3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333

B = 1 << 20
table = dict()
gx1 = 1
for x1 in xrange(0, B):
    temp = gmpy2.c_mod(gmpy2.invert(gx1, p) * h, p) + p
    table[temp] = x1
    gx1 = gmpy2.c_mod(gx1 * g, p) + p

print len(table)

res = 1
for x0 in xrange(0, B):
    # print res
    if res in table:
        x1 = table[res]
        print x1
        print x0
        print x0 * B + x1
        break
    res = gmpy2.c_mod(res * gx1, p) + p

