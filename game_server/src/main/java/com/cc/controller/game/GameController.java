package com.cc.controller.game;

import com.cc.common.annotation.IgnoreToken;
import com.cc.common.result.PageResult;
import com.cc.controller.BaseController;
import com.cc.model.game.Game;
import com.cc.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping({"/game"})
public class GameController extends BaseController {
    private static final long EXPIRE_TIME = 1000L;
    private static final int BUFFER_LENGTH = 1024;
    private static final Pattern RANGE_PATTERN = Pattern.compile("start");
    @Autowired
    private GameService gameService;


    @IgnoreToken
    @ApiIgnore
    @GetMapping({"/index/{id}"})
    public ModelAndView game(@PathVariable("id") Long id, Model model) {
        Game game = this.gameService.selectByPrimaryKey(id);
        Map<String, Object> map = new HashMap();
        String viewName = "game/index";
        if (game == null) {
            viewName = "game/noGame";
            map.put("msg", id + " 该游戏id不存在");
        } else {
            map.put("name", game.getName());
            map.put("title", game.getTitle());
            map.put("desc", game.getDescription());
            if (game.getPicture() != null) {
                map.put("img", game.getPicture());
            } else {
                map.put("img", "https://cdn.steamstatic.com.8686c.com/steam/apps/570/ss_27b6345f22243bd6b885cc64c5cda74e4bd9c3e8.600x338.jpg?t=1536248487");
            }

            map.put("video", game.getVideo() != null ? "../readVideo/" + game.getId() : null);
        }

        model.addAllAttributes(map);
        return new ModelAndView(viewName, "model", model);
    }

    @IgnoreToken
    //@ApiIgnore
    @GetMapping({"/readVideo/{id}"})
    public void readSource(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Game game = this.gameService.selectByPrimaryKey(id);
        String path = game.getVideo();
        File file = new File(path);
        Path video = Paths.get(path);
        int length = (int) Files.size(video);
        int start = 0;
        int end = length - 1;
        String range = request.getHeader("Range");
        range = range == null ? "" : range;
        Matcher matcher = RANGE_PATTERN.matcher(range);
        if (matcher.matches()) {
            String startGroup = matcher.group("start");
            start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
            start = start < 0 ? 0 : start;
            String endGroup = matcher.group("end");
            end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
            end = end > length - 1 ? length - 1 : end;
        }

        int contentLength = end - start + 1;
        response.reset();
        response.setBufferSize(BUFFER_LENGTH);
        response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", file.getName()));
        response.setHeader("Accept-Ranges", "bytes");
        response.setDateHeader("Last-Modified", Files.getLastModifiedTime(video).toMillis());
        response.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
        response.setContentType(Files.probeContentType(video));
        response.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
        response.setHeader("Content-Length", String.format("%s", contentLength));
        response.setStatus(206);
        int bytesLeft = contentLength;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SeekableByteChannel input = Files.newByteChannel(video, StandardOpenOption.READ);
        OutputStream output = response.getOutputStream();
        input.position((long)start);

        int bytesRead;
        while((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
            buffer.clear();
            output.write(buffer.array(), 0, bytesLeft < bytesRead ? bytesLeft : bytesRead);
            bytesLeft -= bytesRead;
        }

    }

    @GetMapping({"/list"})
    public PageResult list(@RequestParam(value = "page",required = false,defaultValue = "1") int page, @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) {
        PageResult result = this.gameService.findListByPage(page, pageSize, null);
        return result;
    }



}