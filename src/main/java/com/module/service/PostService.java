package com.module.service;

import com.module.dto.PostDto;
import com.module.dto.PostResponse;


public interface PostService {
    PostDto createPost(long habitId, PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);


}
