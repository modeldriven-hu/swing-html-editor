package hu.modeldriven.swinghtmleditor.component;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Image {
    public final String md5sum;
    public final String type;
    public final String dataUri;

    public Image(final String md5sum, final String type, final String base64) {
        this.md5sum = md5sum;
        this.type = type;
        this.dataUri = base64;
    }

    public static Image parse(final String type, final InputStream is) {
        final StringWriter out = new StringWriter();
        out.append("data:").append(type).append(";base64,");
        try (final MD5OutputStream os = new MD5OutputStream(new Base64OutputStream(
                new WriterOutputStream(out, StandardCharsets.UTF_8), true, 0, null))) {
            IOUtils.copy(is, os);
            os.flush();
            os.close();
            final String md5sum = os.getHexHash();
            return new Image(md5sum, type, out.getBuffer().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static class MD5OutputStream extends FilterOutputStream {
        private final MessageDigest md5 = DigestUtils.getMd5Digest();
        private byte[] hash = null;

        public MD5OutputStream(final OutputStream out) {
            super(out);
        }

        @Override
        public void write(final byte[] b) throws IOException {
            md5.update(b);
            super.write(b);
        }

        @Override
        public void write(final byte[] b, final int off, final int len) throws IOException {
            md5.update(b, off, len);
            super.write(b, off, len);
        }

        @Override
        public void write(final int b) throws IOException {
            md5.update((byte) b);
            super.write(b);
        }

        @Override
        public void close() throws IOException {
            if (hash == null) {
                hash = md5.digest();
            }
            super.close();
        }

        public byte[] getHash() {
            return hash;
        }

        public String getHexHash() {
            return Hex.encodeHexString(hash);
        }
    }
}
