package com.project.code.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * アプリケーション全体で発生する例外をキャッチして、
 * フロントエンドが扱いやすい形式でエラーレスポンスを返すためのグローバル例外ハンドラー
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 例外1：リクエストボディが不正なJSON構造だった場合に発生
     * 例）空のボディ、JSON構造壊れ、null送信、型不一致など
     *
     * 想定されるレスポンス：
     * {
     *   "status": 400,
     *   "message": "無効な入力: 提供されたデータが不正です"
     * }
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseException(HttpMessageNotReadableException ex) {
    	
        logger.warn("リクエストボディが読み取れません: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "無効な入力: 提供されたデータが不正です");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**石川追記
     * 
     * 例外2：@Valid や @Validated アノテーションによるバリデーション失敗時に発生
     * 各フィールドに対するバリデーションメッセージをまとめて返却
     *
     * 想定されるレスポンス：
     * {
     *   "status": 400,
     *   "message": "バリデーションに失敗しました",
     *   "errors": {
     *     "name": "名前は必須です",
     *     "email": "正しいメールアドレス形式で入力してください"
     *   }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    	
        logger.warn("バリデーションエラー発生: {}", ex.getMessage());

        // フィールドエラー一覧を "フィールド名 → メッセージ" のMapに変換
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (msg1, msg2) -> msg1 // 同じキーがあれば最初のメッセージを優先
                ));

        // レスポンスボディ作成
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "バリデーションに失敗しました");
        response.put("errors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        
    }
    
}
