'use strict';

module.exports = {
    async uploadImage(req, res) {
        const {file} = req;
        try {
            if (file !== undefined) {
                let image_result = await require("../../../util/file")(file.buffer, file.originalname);

                if (image_result.result == false) {
                    return res.status(500).send({status: false, result: "이미지 업로드 실패"});
                }
                return res.send({status: true, result: image_result.fileName});
            }
            return res.status(500).send({status: false, result: "이미지 업로드 실패"});
        } catch (err) {
            return res.status(500).send({status: false, result: "이미지 업로드 실패"});
        }
    }
}