using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Data.Entity;
using System.Web.Http.Results;


namespace BNB.Controllers
{
    /// <summary>
    /// Values controller.
    /// </summary>
    [System.Web.Http.AllowAnonymous]
    [RoutePrefix("api")]
    public class ValuesController : ApiController
    {
        BNBEntityDataModel BNBDB = new BNBEntityDataModel();
        HomeController homeController = new HomeController();

        /// <summary>
        /// Get map markers
        /// </summary>
        /// <returns>Return list of map markers</returns>
        /// <seealso cref="addNewFeedback(Feedback)">Add new feedback</seealso>
        /// <seealso cref="addSensor(Sensor)">Add new sensor</seealso>
        [Route("getMapMarkers")]
        [HttpGet]
        [ActionName("Get map markers")]
        public IEnumerable<Sensor> GetMarkers()
        {
            return homeController.GetMapMarker();
        }

        /// <summary>
        /// Insert new sensor info
        /// </summary>
        /// <returns>Return GUID Key for sensor</returns>
        /// <seealso cref="GetMarkers()">Get all markers</seealso>
        /// <seealso cref="addNewFeedback(Feedback)">Add feedback</seealso>
        [System.Web.Http.Route("addnewsensor")]
        [System.Web.Http.HttpPost]
        [System.Web.Http.ActionName("Add new sensor")]
        public System.Web.Mvc.JsonResult addSensor([FromBody]Sensor sensor)
        {
            return homeController.AddSensorInfo(sensor);
        }

        /// <summary>
        /// Insert new user feedback
        /// </summary>
        /// <returns>Return success or error</returns>
        /// <seealso cref="GetMarkers()">Get map markers</seealso>
        /// <seealso cref="addSensor(Sensor)">Add new sensor</seealso>//////////////////////
        [Route("addfeedback")]
        [HttpPost]
        [ActionName("Add new feedback")]
        public System.Web.Mvc.JsonResult addNewFeedback([FromBody]Feedback feedback)
        {
            return homeController.AddUserFeedback(feedback);
        }

        /// <summary>
        /// Get sensors by keyword
        /// </summary>
        /// <returns>Return sensors</returns>
        /// <seealso cref="GetMarkers()">Get map markers</seealso>
        /// <seealso cref="addSensor(Sensor)">Add new sensor</seealso>
        /// <seealso cref="getSensorByKey(string)">Get sensor by key</seealso>
        [Route("getByKeyword")]
        [HttpGet]
        [ActionName("Get sensors by keyword")]
        public IEnumerable<Sensor> getByKeyword(String keyword)
        {
            return homeController.GetMarkersByKeyWord(keyword);
        }

        /// <summary>
        /// Get sensor by key
        /// </summary>
        /// <returns>Return sensor</returns>
        /// <seealso cref="GetMarkers()">Get map markers</seealso>
        /// <seealso cref="addSensor(Sensor)">Add new sensor</seealso>
        /// <seealso cref="getByKeyword(string)">Get sensors by keyword</seealso>
        [Route("getSensorByKey")]
        [HttpGet]
        [ActionName("Get sensor by key")]
        public Sensor getSensorByKey(String sensorKey)
        {
            return homeController.GetSensorByKey(sensorKey);
        }
    }
}