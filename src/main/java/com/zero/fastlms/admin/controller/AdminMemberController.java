package com.zero.fastlms.admin.controller;

import com.zero.fastlms.admin.dto.MemberDto;
import com.zero.fastlms.admin.model.MemberInput;
import com.zero.fastlms.admin.model.MemberParam;
import com.zero.fastlms.course.controller.BaseController;
import com.zero.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class AdminMemberController extends BaseController {

    private final MemberService memberService;

    @GetMapping("/list.do")
    public String memberList(Model model, MemberParam parameter) {
        parameter.init();

        List<MemberDto> members = memberService.getList(parameter);

        long totalCount = 0;
        if (members != null && !members.isEmpty()) {
            totalCount = members.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/member/list";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, MemberParam parameter) {
        parameter.init();

        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member", member);

        return "admin/member/detail";
    }

    @PostMapping("/status.do")
    public String status(Model mode, MemberInput parameter) {

        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }

    @PostMapping("/password.do")
    public String password(Model mode, MemberInput parameter) {

        boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getPassword());

        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }


}
