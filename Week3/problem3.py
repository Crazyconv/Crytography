from Crypto.Hash import SHA256
def hash_video(url):
    with open(url, 'rb') as video:
        whole = video.read()
        block_num = (len(whole) + 1023) / 1024 - 1

        value = b''
        while block_num >= 0:
            h = SHA256.new()
            h.update(whole[block_num*1024:block_num*1024+1024]+value);
            value = h.digest()
            block_num = block_num - 1
        return value.encode('hex')


if __name__ == "__main__":
    print hash_video("/Users/Crazyconv/Desktop/video.mp4");