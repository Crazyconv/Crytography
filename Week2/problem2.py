from Crypto.Cipher import AES
import binascii

# hex string to byte string and reverse
# hex string to int
# int to string and reverse

def encrypt_CBC(IV, key, pt):
    # IV here is hex
    key = key.decode('hex')
    block_num = len(pt) / 16
    padding_num = 16 * (block_num + 1) - len(pt)
    for i in xrange(padding_num):
        pt = pt + binascii.unhexlify("%02x" % padding_num)
    ct = IV

    last = IV
    for i in xrange(block_num + 1):
        int_block = int(last, 16) ^ int(binascii.hexlify(pt[i*16:i*16+16]),16)
        string_block = binascii.unhexlify("%032x" % int_block)
        cipher = AES.new(key, AES.MODE_ECB)
        last = cipher.encrypt(string_block).encode('hex')
        ct = ct + last
    return ct


def decrypt_CBC(key, ct):
    key = key.decode('hex')  # to byte string (needed in AES API)
    ct = ct.decode('hex')
    start = len(ct) / 16 - 1
    pt = ""
    while start >= 1:
        cipher = AES.new(key, AES.MODE_ECB)
        byte_block = cipher.decrypt(ct[start*16: start*16+16])
        int_block = int(byte_block.encode('hex'), 16) ^ int(ct[start*16-16: start*16].encode('hex'), 16)
        pt = binascii.unhexlify("%032x" % int_block) + pt  # from int to string
        start = start - 1
    padding_num = int(binascii.hexlify(pt[-1]), 16)
    return pt[0:len(pt)-padding_num], ct[0: 16].encode('hex')

def encrypt_CTR(IV, key, pt):
    # IV here is int
    key = key.decode('hex')
    ct = ("%032x" % IV)
    block_num = len(pt) / 16;
    start = 0
    while start < block_num:
        cipher = AES.new(key, AES.MODE_ECB)
        byte_block = cipher.encrypt(binascii.unhexlify("%032x" % (IV+start)))
        int_block = int(byte_block.encode('hex'), 16) ^ int(binascii.hexlify(pt[start*16:start*16+16]), 16)
        ct = ct + ("%032x" % int_block)
        start = start + 1
    # last block
    last_block_len = len(pt) - block_num * 16
    if last_block_len > 0:
        byte_block = cipher.encrypt(binascii.unhexlify("%032x" % (IV+start)))[0:last_block_len]
        int_block = int(byte_block.encode('hex'), 16) ^ int(binascii.hexlify(pt[block_num * 16:]), 16)
        ct = ct + ("%032x" % int_block)[32 - 2 * last_block_len:32]
    return ct

def decrypt_CTR(key, ct):   # !!! 1 byte is 2 chars in hex
    key = key.decode('hex')
    pt = ""
    IV = int(ct[0:32], 16)
    block_num = len(ct) / 32 - 1;
    start = 0
    while start < block_num:
        cipher = AES.new(key, AES.MODE_ECB)
        byte_block = cipher.encrypt(binascii.unhexlify("%032x" % (IV+start)))
        int_block = int(byte_block.encode('hex'), 16) ^ int(ct[start*32+32:start*32+64], 16)
        pt = pt + binascii.unhexlify("%032x" % int_block)  # from int to string
        start = start + 1
    # last block
    last_block_len = len(ct) - (block_num + 1) * 32
    if last_block_len > 0:
        byte_block = cipher.encrypt(binascii.unhexlify("%032x" % (IV+start)))[0:last_block_len/2]
        int_block = int(byte_block.encode('hex'), 16) ^ int(ct[block_num * 32 + 32:], 16)
        pt = pt + binascii.unhexlify(("%032x" % int_block)[32-last_block_len:32])
    return pt, IV



def main(key, ct, mode):
    if mode == 0:
        pt, IV = decrypt_CBC(key, ct)
        print(pt)
        # ct2 = encrypt_CBC(IV, key, pt)
        # print(ct2)
    else:
        pt, IV = decrypt_CTR(key, ct);
        print(pt)
        # ct2 = encrypt_CTR(IV, key, pt);
        # print(ct2)
    # if ct == ct2:
    #     print("true")
    # else:
    #     print("false")
    print

if __name__ == "__main__":
    # 0: CBC; 1: CTR
    main("140b41b22a29beb4061bda66b6747e14","4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81", 0)
    main("140b41b22a29beb4061bda66b6747e14","5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253", 0)
    main("36f18357be4dbd77f050515c73fcf9f2","69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329", 1)
    main("36f18357be4dbd77f050515c73fcf9f2","770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451", 1)
