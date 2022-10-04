package com.board.util;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.constant.Method;

import java.util.Map;

@Controller
public class UiUtils {

    public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message,
                                          @RequestParam(value = "redirectUri", required = false) String redirectUri,
                                          @RequestParam(value = "method", required = false) Method method,
                                          @RequestParam(value = "pparams", required = false) Map<String,Object> params, Model model){
        model.addAttribute("message", message); //얼럿 메시지
        model.addAttribute("redirectUri", redirectUri); //리다이렉트 URI (완료 후, 얼럿 노출 후, 페이지로 리다이렉트용)
        model.addAttribute("method", method); //Enum 클래스에 선언한 HTTP요청 메서드
        model.addAttribute("params", params); //view로 전달할 파라미터(해시맵)

        return "utils/message-redirect";
    }

}
