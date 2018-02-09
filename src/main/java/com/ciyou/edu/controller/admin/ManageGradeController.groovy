package com.ciyou.edu.controller.admin

import com.ciyou.edu.entity.Grade
import com.ciyou.edu.service.GradeService
import net.sf.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

/**
 * @Author C.
 * @Date 2018-02-09 18:13
 */
@Controller
class ManageGradeController {

    private static final Logger logger = LoggerFactory.getLogger(ManageGradeController.class)

    @Autowired
    private GradeService gradeService

    @RequestMapping("/admin/manageGrade")
    ModelAndView manageGrade(){
        ModelAndView mv = new ModelAndView("admin/manageGrade")
        List<Grade> gradeList = gradeService?.findAllGrade()
        mv?.addObject("grades",gradeList)
        return mv
    }

    @RequestMapping(value="/admin/addGrade",method=RequestMethod.POST)
    @ResponseBody
    String addGrade(Grade grade){
        //校验数据
        if(!grade?.gradeName || grade?.getGradeName()?.trim() == ""){
            return "年级不能为空"
        }else{
            try{
                if(gradeService?.addGrade(grade)){
                    return "添加成功"
                }else{
                    return "添加失败"
                }
            }catch (Exception e){
                logger.info("添加Grade错误：" + e.getMessage())
                return "添加失败，请重试"
            }
        }
    }

    @RequestMapping(value="/admin/updateGrade",method=RequestMethod.POST)
    @ResponseBody
    String updateGrade(Grade grade){
        //校验数据
        if(!grade?.gradeName || grade?.getGradeName()?.trim() == ""){
            return "年级不能为空"
        }else{
            try{
                if(gradeService?.updateGrade(grade)){
                    return "修改成功"
                }else{
                    return "修改失败"
                }
            }catch (Exception e){
                logger.info("修改Grade错误：" + e.getMessage())
                return "修改失败，请重试"
            }
        }
    }

    @RequestMapping(value="/admin/getGrade",method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    String getGrade(Integer gradeId){
        Grade grade = gradeService?.getGrade(gradeId)
        logger.info("获得指定的Grade：" + grade)
        //这里要转为json对象，前端ajax才解析的了
        JSONObject jsonObject = JSONObject.fromObject(grade)
        return jsonObject.toString()
    }

    @RequestMapping(value="/admin/deleteGrade",method=RequestMethod.POST)
    @ResponseBody
    String deleteGrade(Integer gradeId){
        try{
            if(gradeService?.deleteGrade(gradeId)){
                return "删除成功"
            }else{
                return "删除失败"
            }
        }catch (Exception e){
            logger.info("删除Grade错误：" + e.getMessage())
            return "删除失败，请重试"
        }
    }

}
