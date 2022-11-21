import { useEffect, useState } from "react";
import { useRecoilValue, useSetRecoilState } from "recoil";
import PostBox from "../../component/UC-03-Board/PostBox";
import { categoryNowState } from "../../recoil/CommonRecoil";
import { postNowState } from "../../recoil/UC-03-Board";
import { categoryNameToIcon } from "../CommonContainer";

const PostBoxContainer = (props) => {
    const { navigation, item, id } = props;
    const postNow = useRecoilValue(postNowState);
    const setPostNow = useSetRecoilState(postNowState);
    const icon = categoryNameToIcon(item.information.category);
    const propsData = { navigation, item, id, postNow, setPostNow, icon };
    return <PostBox {...propsData} />;
};

export default PostBoxContainer;