package com.xstudio.tool.utils.wartermarker;

import java.awt.*;

/**
 * @author xiaobiao
 * @date 2019/1/11
 */
public class TextProperty {
    /**
     * 字体名称
     */
    private String fontName;

    /**
     * 字体类型
     *
     * @see java.awt.Font
     */
    private Integer style;

    /**
     * 尺寸
     */
    private Integer size;

    /**
     * 透明度
     */
    private Float alpha;

    /**
     * 文本内容
     */
    private String text;

    /**
     * 在图片上的X偏移量
     */
    private Integer x;

    /**
     * 在图片上的Y偏移量
     */
    private Integer y;

    /**
     * 颜色
     */
    private Color color;

    /**
     * 旋转
     */
    private TextRoate roate;

    /**
     * Getter for property 'fontName'.
     *
     * @return Value for property 'fontName'.
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * Setter for property 'fontName'.
     *
     * @param fontName Value to set for property 'fontName'.
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    /**
     * Getter for property 'style'.
     *
     * @return Value for property 'style'.
     */
    public Integer getStyle() {
        return style;
    }

    /**
     * Setter for property 'style'.
     *
     * @param style Value to set for property 'style'.
     */
    public void setStyle(Integer style) {
        this.style = style;
    }

    /**
     * Getter for property 'size'.
     *
     * @return Value for property 'size'.
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Setter for property 'size'.
     *
     * @param size Value to set for property 'size'.
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Getter for property 'alpha'.
     *
     * @return Value for property 'alpha'.
     */
    public Float getAlpha() {
        return alpha;
    }

    /**
     * Setter for property 'alpha'.
     *
     * @param alpha Value to set for property 'alpha'.
     */
    public void setAlpha(Float alpha) {
        this.alpha = alpha;
    }

    /**
     * Getter for property 'text'.
     *
     * @return Value for property 'text'.
     */
    public String getText() {
        return text;
    }

    /**
     * Setter for property 'text'.
     *
     * @param text Value to set for property 'text'.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for property 'x'.
     *
     * @return Value for property 'x'.
     */
    public Integer getX() {
        return x;
    }

    /**
     * Setter for property 'x'.
     *
     * @param x Value to set for property 'x'.
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Getter for property 'y'.
     *
     * @return Value for property 'y'.
     */
    public Integer getY() {
        return y;
    }

    /**
     * Setter for property 'y'.
     *
     * @param y Value to set for property 'y'.
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Getter for property 'color'.
     *
     * @return Value for property 'color'.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for property 'color'.
     *
     * @param color Value to set for property 'color'.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for property 'roate'.
     *
     * @return Value for property 'roate'.
     */
    public TextRoate getRoate() {
        return roate;
    }

    /**
     * Setter for property 'roate'.
     *
     * @param roate Value to set for property 'roate'.
     */
    public void setRoate(TextRoate roate) {
        this.roate = roate;
    }
}
