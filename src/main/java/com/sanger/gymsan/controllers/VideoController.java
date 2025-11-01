package com.sanger.gymsan.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.models.Video;
import com.sanger.gymsan.services.VideoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController extends BaseController<Video, Long, VideoService> {

}
