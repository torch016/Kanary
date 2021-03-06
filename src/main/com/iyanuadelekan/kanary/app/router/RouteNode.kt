package com.iyanuadelekan.kanary.app.router

import com.iyanuadelekan.kanary.app.RouteList
import com.iyanuadelekan.kanary.app.RouterAction
import com.iyanuadelekan.kanary.app.adapter.component.middleware.MiddlewareAdapter
import com.iyanuadelekan.kanary.app.lifecycle.AppContext

/**
 * @author Iyanu Adelekan on 18/11/2018.
 */
internal class RouteNode(val path: String, var action: RouterAction? = null) {

    private val children = RouteList()
    private var middleware: ArrayList<MiddlewareAdapter> = ArrayList()

    /**
     * Adds a child node to the current node.
     *
     * @param routeNode - node to be added.
     */
    fun addChild(routeNode: RouteNode) = this.children.add(routeNode)

    /**
     * Invoked to check if a route node has a specified child node.
     *
     * @param path - path to be matched.
     * @return [Boolean] - true if child exists and false otherwise.
     */
    fun hasChild(path: String): Boolean {
        children.forEach {
            if (it.path == path) {
                return true
            }
        }
        return false
    }

    /**
     * Gets a child node matching a specific path.
     *
     * @params path - path to be matched.
     * @return [RouteNode] - node if one exists and null otherwise.
     */
    fun getChild(path: String): RouteNode? {
        children.forEach {
            if (it.path == path) {
                return it
            }
        }
        return null
    }

    /**
     * Gets children of given node.
     *
     * @return [RouteList] - children.
     */
    fun getChildren(): RouteList = this.children

    /**
     * Returns number of child nodes.
     *
     * @return [Int] - number of child nodes.
     */
    fun getChildCount(): Int = children.size

    /**
     * Invoked to add a collection of middleware to route node.
     *
     * @param middleware - middleware to be added.
     */
    fun addMiddleware(middleware: List<MiddlewareAdapter>) {
        this.middleware.addAll(middleware)
    }

    fun runMiddleWare(ctx: AppContext) {
        middleware.forEach { it.run(ctx) }
    }

    fun executeAction(ctx: AppContext) {
        action?.invoke(ctx)
    }

    /**
     * Converts [RouteNode] to its corresponding string representation.
     *
     * @return [String] - String representation of route node.
     */
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("$path => [")

        if (!children.isEmpty()) {
            for (i in 0 until children.size) {
                builder.append(children[i])

                if (i != children.size - 1) {
                    builder.append(",")
                }
            }
        }
        builder.append("]")

        return builder.toString()
    }
}