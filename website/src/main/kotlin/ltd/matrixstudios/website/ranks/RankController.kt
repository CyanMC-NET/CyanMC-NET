package ltd.matrixstudios.website.ranks

import ltd.matrixstudios.website.user.AlchemistUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Alchemist
 * @website https://solo.to/redis
 */
@Controller
class RankController @Autowired constructor(private val repository: RankRepository) {
    @RequestMapping(value = ["/api/ranks"], method = [RequestMethod.GET])
    fun getAllRanks(request: HttpServletRequest): ModelAndView
    {
        val modelAndView = ModelAndView("ranks")

        val profile = request.session.getAttribute("profile") as AlchemistUser? ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")
        if (!profile.hasPermission("alchemist.website.ranks")) throw ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to view this page.")
        val ranks = repository.findAll();

        modelAndView.addObject("section", "ranks")
        modelAndView.addObject("ranks", ranks)
        return modelAndView
    }
}