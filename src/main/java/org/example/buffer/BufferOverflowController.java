package org.example.buffer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Controller
public class BufferOverflowController {

    @GetMapping("/buffer/array")
    @ResponseBody
    public String arrayOverflow(@RequestParam String input) {
        byte[] buffer = new byte[10];
        byte[] inputBytes = input.getBytes();
        
        for (int i = 0; i < inputBytes.length; i++) {
            buffer[i] = inputBytes[i];
        }
        return "Buffer: " + Arrays.toString(buffer);
    }

    @GetMapping("/buffer/bytebuffer")
    @ResponseBody
    public String byteBufferOverflow(@RequestParam String data) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] dataBytes = data.getBytes();
        
        buffer.put(dataBytes);
        return "Buffer: " + Arrays.toString(buffer.array());
    }

    @GetMapping("/buffer/string")
    @ResponseBody
    public String stringBufferOverflow(@RequestParam String text) {
        char[] buffer = new char[5];
        
        for (int i = 0; i < text.length(); i++) {
            buffer[i] = text.charAt(i);
        }
        return "Buffer: " + Arrays.toString(buffer);
    }
}