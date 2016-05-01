import urllib2
import sys
import binascii

TARGET = 'http://crypto-class.appspot.com/po?er='
#--------------------------------------------------------------
# padding oracle
#--------------------------------------------------------------
def query(q):
    target = TARGET + urllib2.quote(q)    # Create query URL
    req = urllib2.Request(target)         # Send HTTP request to server
    try:
        f = urllib2.urlopen(req)          # Wait for response
    except urllib2.HTTPError, e:          
        # print "We got: %d" % e.code       # Print response code
        if e.code == 404:
            return True # good padding
        return False # bad padding

def xor(ct_list, hex_byte, pos, val):
    xor_int = int(hex_byte, 16) ^ val
    xor_byte = ("%02x" % xor_int)
    ct_list[pos] = xor_byte[0]
    ct_list[pos + 1] = xor_byte[1]

def per_block(ct, start, last):
    pt = ""
    pad = 1
    ct_list = list(ct)
    while pad <= 16:
        ct_copy = list(ct_list)
        temp = 1
        while temp <= pad:
            hex_byte = ''.join(ct_copy[start + (16 - temp) * 2: start + (16 - temp) * 2 + 2])
            xor(ct_copy, hex_byte, start + (16 - temp) * 2, pad)
            temp += 1
        hex_byte = ''.join(ct_copy[start + (16 - pad) * 2: start + (16 - pad) * 2 + 2])
        guess = 127
        while guess >= 0:
            # print guess
            xor(ct_copy, hex_byte, start + (16 - pad) * 2, guess)
            if query(''.join(ct_copy)):
                if last and pad == 1: # last block
                    while pad <= guess:
                        hex_byte = ''.join(ct_list[start + (16 - pad) * 2: start + (16 - pad) * 2 + 2])
                        xor(ct_list, hex_byte, start + (16 - pad) * 2, guess)
                        pad += 1
                else:
                    hex_byte = ''.join(ct_list[start + (16 - pad) * 2: start + (16 - pad) * 2 + 2])
                    xor(ct_list, hex_byte, start + (16 - pad) * 2, guess)
                    pt = ("%02x" % guess) + pt
                    print pad, ":", guess
                    pad += 1
                break
            guess -= 1
        if guess == -1:
            print pad, ": not found"
    # print pt
    return pt


def padding_oracle(ct):
    pt = ""
    block_num = len(ct) * 4 / 128
    pt = per_block(ct[0: block_num * 32], (block_num - 2) * 32, True) + pt # last block, it has padding
    block_num -= 1
    while block_num >= 2:
        pt = per_block(ct[0: block_num * 32], (block_num - 2) * 32, False) + pt
        block_num -= 1
    print pt
    return binascii.unhexlify(pt)


if __name__ == "__main__":
    pt = padding_oracle("f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4")
    print pt