package com.zero.fastlms.course.controller;

import com.zero.fastlms.course.dto.TakeCourseDto;
import com.zero.fastlms.course.model.ServiceResult;
import com.zero.fastlms.course.model.TakeCourseParam;
import com.zero.fastlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/takeCourse")
public class AdminTakeCourseController extends BaseController{

    private final TakeCourseService takeCourseService;

    @GetMapping("/list.do")
    public String list(Model model, TakeCourseParam parameter) {
        parameter.init();

        List<TakeCourseDto> takeCourseList = takeCourseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(takeCourseList)) {
            totalCount = takeCourseList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("takeCourseList", takeCourseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        System.out.println("list -> " + takeCourseList.toString());

        return "admin/takeCourse/list";
    }

    @PostMapping("/status.do")
    public String status(Model model, TakeCourseParam parameter) {

        ServiceResult result = takeCourseService.updateStatus(parameter.getId(), parameter.getStatus());
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/admin/takeCourse/list.do";
    }


}